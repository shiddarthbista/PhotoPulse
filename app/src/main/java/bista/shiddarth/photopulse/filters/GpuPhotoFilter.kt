package bista.shiddarth.photopulse.filters

import android.graphics.PointF
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorInvertFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGlassSphereFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGrayscaleFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSketchFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter

enum class GpuPhotoFilter(val label: String, val filter: GPUImageFilter) {
    NONE("None", GPUImageFilter()),
    GRAYSCALE("Grayscale", GPUImageGrayscaleFilter()),
    SEPIA("Sepia", GPUImageSepiaToneFilter()),
    INVERT("Invert", GPUImageColorInvertFilter()),
    VIGNETTE("Vignette", GPUImageVignetteFilter(PointF(0.5f, 0.5f), floatArrayOf(0f, 0f, 0f), 0.3f, 0.75f)),
    BRIGHTNESS("Bright", GPUImageBrightnessFilter(0.3f)),
    TOON("Toon", GPUImageToonFilter()),
    POSTERIZE("Posterize", GPUImagePosterizeFilter()),
    GLASS("Glass", GPUImageGlassSphereFilter()),
    SKETCH("Sketch", GPUImageSketchFilter()),
}