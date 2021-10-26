package com.xwq.companyvxwhelper.base.Enum

enum class PermissionMode(permissionName : String) {
    /**
     * 权限模式
     * 1： 必须通过的权限
     * 2： 可选权限
     */
    REQUIRED("required"),OPTIONAL("optional")
}