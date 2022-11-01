package it.hembik.stargazers.tools

import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import java.io.File
import java.lang.reflect.Type

abstract class FakerBase {

    protected inline fun <reified T> genericType(): Type = object: TypeToken<T>() {}.type

    protected inline fun <reified T> responseFromJson(json: String): T =
        Gson().fromJson<T>(json, genericType<T>())

    protected inline fun <reified T> readFromFile(src: String): T {
        // WARNING
        // javaClass.getResourceAsStream is not available here
        val content = File(src).readText()
        return responseFromJson<T>(content)
    }
}