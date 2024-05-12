package display.view.spaceinvaderview;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Logger;

public class Scale {

    // Logger for test purposes
    private static final Logger LOGGER = Logger.getLogger(Scale.class.getName());

    /**
     * Calculates the ratio to apply to the scale of the map based on the screen resolution
     * @return an integer to apply to the scale
     */
    public static int getRatioForResolution() {

        // Get screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        LOGGER.info("Screen size: " + screenWidth + "x" + screenHeight);

        // Default values for 1920*1080 resolution used as references (125% scaling windows 11 settings)
        double default_scale = 14;
        double default_ratio = 16.0/9.0;

        // Current values for the scale
        double current_dpi = getScreenDPI();
        double dpiDifference = current_dpi - 126.0; // default_dpi for 1920*1080
        double percentageMultiplierScale = percentageMultiplier(126.0, current_dpi);
        double current_scale = default_scale * percentageMultiplierScale;

        System.out.println(" Current DPI : " + current_dpi);
        System.out.println(" DPI Difference : " + dpiDifference);
        System.out.println(" Percentage Multiplier Scale : " + percentageMultiplierScale);
        System.out.println(" Current Scale : " + current_scale);

        // Current values for the ratio
        double current_ratio = screenWidth / screenHeight;
        double percentageMultiplierRatio = percentageMultiplierRatio(default_ratio, current_ratio);
        double current_ratio_multiplier = percentageMultiplierRatio;
        double final_multiplier = current_ratio_multiplier * current_scale;

        System.out.println(" Current Ratio : " + screenWidth + "/" + screenHeight);
        System.out.println(" Percentage Multiplier Ratio : " + percentageMultiplierRatio);
        System.out.println(" Current Ratio Multiplier : " + current_ratio_multiplier);
        System.out.println(" Final Multiplier : " + (int)Math.ceil(final_multiplier));

        // Just in case someone is still living in 2003
        int lastCorrection = (current_dpi < 110.0) ? -1 : 0;
        int lastCorrection2 = (current_dpi < 70.0) ? -1 : 0;
        return (int)Math.ceil(final_multiplier + lastCorrection + lastCorrection2);
    }

    /**
     * Get the screen DPI
     * @return DPI value of the screen
     */
    private static double getScreenDPI() {
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

    /**
     * Calculates the percentage multiplier based on the difference between a reference dpi and the current dpi
     * @param default_dpi a reference dpi value with which the program was designed
     * @param current_dpi the current dpi value of the screen
     * @return the percentage multiplier to apply to the scale
     */
    private static double percentageMultiplier(double default_dpi, double current_dpi) {
        double dpiDifference = current_dpi - default_dpi;
        double dpiDifferencePercentage = dpiDifference / default_dpi * 100;
        double dpiPercentageMultiplier = (100 + dpiDifferencePercentage) / 100;
        return dpiPercentageMultiplier;
    }

    /**
     * Calculates the percentage multiplier squared based on the difference between a reference ratio and the current ratio
     * @param default_ratio a reference ratio value with which the program was designed
     * @param current_ratio the current ratio value of the screen
     * @return the percentage multiplier to apply to the scale
     */
    private static double percentageMultiplierRatio(double current_ratio, double default_ratio) {
        double ratioDifference = current_ratio - default_ratio;
        double ratioDifferencePercentage = ratioDifference / default_ratio * 100;
        double ratioPercentageMultiplier = (100 + ratioDifferencePercentage) / 100;
        return ratioPercentageMultiplier * ratioPercentageMultiplier;
    }
}

