package com.better_markdown.bettermarkdown.notifications

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

class BetterMarkdownNotify {
    fun notifyWarning(project: Project, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Better-Markdown")
            .createNotification(content, NotificationType.WARNING)
            .notify(project)
    }
}