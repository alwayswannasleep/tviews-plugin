package com.vkraevskiy.tviews

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GenerateTask extends DefaultTask {

    @TaskAction
    def generateStringXml() {
        println("\n----- generateFontNames ------\n")

        project.rootProject.allprojects.each { p ->
            if (project.rootDir.absolutePath == p.projectDir.absolutePath) {
                return
            }

            File fontsStringXml = new File(p.projectDir, "src/main/res/values/generated_fonts.xml")
            if (fontsStringXml.exists()) {
                if (!fontsStringXml.delete()) {
                    println("-- some error deleting ${fontsStringXml.getAbsolutePath()}")
                }
            }

            File assets = new File(p.projectDir, "src/main/assets")

            File fontsDir = new File(assets, "fonts")

            if (!fontsDir.exists() || !fontsDir.isDirectory()) {
                println("\n----- fonts dirs not found for ${p.project} -----")
                return
            }

            println("finded fonts directory: ${fontsDir.absolutePath}")

            def fonts = new HashSet<String>()
            fontsDir.listFiles().each {
                fonts.add(it.getName())
                println("font: ${it.getName()}")
            }

            if (fonts.isEmpty()) {
                println("\n----- fonts not found for ${p.project} -----")
                return
            }

            if (!fontsStringXml.createNewFile()) {
                println("\n----- can't create xml file for ${p.project} -----")
                return
            }

            StringBuilder builder = new StringBuilder(
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<!-- Don't edit this file. It's generated at every build. -->\n" +
                            "<resources>"
            )

            fonts.each {
                String xmlFontName = "font_" + it.substring(0, it.lastIndexOf(".")).toLowerCase().replace("-", "_")

                builder.append("\n   <string name=\"$xmlFontName\">$it</string>")
            }

            builder.append("\n</resources>")

            fontsStringXml.write(builder.toString())

            println("\n----- xml file created succesfully ------")
        }

        println("\n----- generateFontNames finished ------\n")
    }
}
