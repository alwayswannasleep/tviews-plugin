package com.vkraevskiy.tviews

import org.gradle.api.Plugin
import org.gradle.api.Project

class TViewsPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.tasks.create("prepareFontStringsXml", GenerateTask.class)
    }
}
