package it.hembik.stargazers.tools

import it.hembik.data.dto.StargazerDTO

class FakerStargazers(): FakerBase() {
    fun getStargazers(): List<StargazerDTO> {
        val sourceFile = "src/test/res/raw/stargazers.json"
        return readFromFile(sourceFile)
    }
}