package com.saurabhbadola.statesman_annotation_processor


import com.google.auto.service.AutoService
import com.saurabhbadola.statesman.annotations.State
import com.saurabhbadola.statesman.annotations.StateField
import com.saurabhbadola.statesman_annotation_processor.codegen.StateCodeBuilder
import com.saurabhbadola.statesman_annotation_processor.models.PersistentFieldData
import com.saurabhbadola.statesman_annotation_processor.models.StateData

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.io.File
import javax.annotation.processing.*
import javax.annotation.processing.Processor
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMap
import kotlin.reflect.jvm.internal.impl.name.FqName


@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

    lateinit var messager: Messager
    lateinit var elementUtil: Elements

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        messager = p0!!.messager
        elementUtil = p0.elementUtils
    }

    override fun process(p0: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {

        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
                ?: return false
//        if (!(roundEnv?.processingOver())!!) {
//      The first step is for the processor to identify all code elements annotated with AdapterModel.
            roundEnv?.getElementsAnnotatedWith(State::class.java)
                ?.forEach {
                    val modelData = getStateData(it)
                    val fileName = "${modelData.modelName}State" // 1
                    FileSpec.builder(modelData.packageName, fileName) // 2
                        .addType(StateCodeBuilder(fileName, modelData).build()) // 3
                        .build()
                        .writeTo(File("$kaptKotlinGeneratedDir/.."))
                // 4
                }
//        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(State::class.java.canonicalName)
    }


    private fun getStateData(elem: Element): StateData {

        val packageName = processingEnv.elementUtils.getPackageOf(elem)
            .toString() // Extracts the package name from the element.
        val stateName = elem.simpleName.toString() // Extracts the package name from the element.

        val persistentFieldsList = arrayListOf<PersistentFieldData>()
        elem.enclosedElements.forEach {
            val persistentField = it.getAnnotation(StateField::class.java)
            if (persistentField != null) {
                val elementName = it.simpleName.toString()
                val fieldType = it.asType().asTypeName().javaToKotlinType()
                persistentFieldsList.add(
                    PersistentFieldData(
                        elementName,
                        fieldType
                    )
                ) // Otherwise, collects the child element’s name and viewId from its annotation.
            }
        }

        persistentFieldsList.add(PersistentFieldData("isLoading", ClassName("kotlin", "Boolean")))
        persistentFieldsList.add(PersistentFieldData("errorMessage", ClassName("kotlin", "String")))
        persistentFieldsList.add(PersistentFieldData("alertMessage", ClassName("kotlin", "String")))
        persistentFieldsList.add(
            PersistentFieldData(
                "loadingMessage",
                ClassName("kotlin", "String")
            )
        )
        messager.printMessage(Diagnostic.Kind.NOTE, persistentFieldsList.toString())

        return StateData(
            packageName,
            stateName,
            persistentFieldsList
        ) // Packs all this info into a ModelData instance.
    }


    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    private fun TypeName.javaToKotlinType(): TypeName = if (this is ParameterizedTypeName) {
        (rawType.javaToKotlinType() as ClassName).parameterizedBy(
            *typeArguments.map { it.javaToKotlinType() }.toTypedArray()
        )
    } else {
        val className = JavaToKotlinClassMap.INSTANCE
            .mapJavaToKotlin(FqName(toString()))?.asSingleFqName()?.asString()
        if (className == null) this
        else ClassName.bestGuess(className)
    }

}