
package com.highstreets.user.ui.help;


import com.highstreets.user.common.CommonViewInterface;
import com.highstreets.user.models.Help;

import java.util.ArrayList;

public interface HelpActivityViewInterface extends CommonViewInterface {

    void onLoadingHelpSuccess(ArrayList<Help> help);

    void onLoadingHelpFailed(String message);
}

