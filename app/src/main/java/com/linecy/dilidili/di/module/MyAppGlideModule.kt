package com.linecy.dilidili.di.module

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * 解决警告：Failed to find GeneratedAppGlideModule.
 * You should include an annotationProcessor compile dependency
 * on com.github.bumptech.glide:compiler in your application and
 * a @GlideModule annotated AppGlideModule implementation or
 * LibraryGlideModules will be silently ignored.
 *
 * @author by linecy.
 *
 * @link http://sjudd.github.io/glide/doc/generatedapi.html
 */


@GlideModule
class MyAppGlideModule : AppGlideModule()