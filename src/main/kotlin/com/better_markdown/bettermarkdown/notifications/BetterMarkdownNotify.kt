package com.better_markdown.bettermarkdown.notifications

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

object BetterMarkdownNotify {
    fun notifyWarning(project: Project, title: String, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Better-Markdown")
            .createNotification(title, content, NotificationType.WARNING)
            .notify(project)
    }

    fun notifyInformation(project: Project, title: String, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Better-Markdown")
            .createNotification(title, content, NotificationType.INFORMATION)
            .notify(project)
    }

    fun notifyError(project: Project, title: String, content: String) {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("Better-Markdown")
            .createNotification(title, content, NotificationType.ERROR)
            .notify(project)
    }
}