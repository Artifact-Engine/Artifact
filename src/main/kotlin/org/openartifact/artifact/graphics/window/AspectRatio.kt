package org.openartifact.artifact.graphics.window

import org.slf4j.LoggerFactory

enum class AspectRatio(val ratio : Float) {

    RATIO_1_1(1f / 1f),
    RATIO_3_2(3f / 2f),
    RATIO_4_1(4f / 1f),
    RATIO_4_3(4f / 3f),
    RATIO_5_4(5f / 4f),
    RATIO_8_5(8f / 5f),
    RATIO_16_9(16f / 9f),
    RATIO_21_9(21f / 9f),
    RATIO_32_9(32f / 9f),
    RATIO_64_27(64f / 27f),
    RATIO_256_135(256f / 135f);

    companion object {

        private val logger = LoggerFactory.getLogger(javaClass)

        fun findClosestAspectRatio(width : Int, height : Int) : AspectRatio {
            logger.info("Trying to calculate aspect ratio")
            val calculatedRatio = width.toFloat() / height.toFloat()
            var closestRatio = RATIO_1_1
            var smallestDifference = Float.MAX_VALUE

            for (ratio in entries) {
                val difference = kotlin.math.abs(ratio.ratio - calculatedRatio)
                if (difference < smallestDifference) {
                    smallestDifference = difference
                    closestRatio = ratio
                }
            }

            logger.info("Aspect ratio detected: $closestRatio")

            return closestRatio
        }
    }

}
