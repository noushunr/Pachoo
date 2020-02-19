
package com.presentation.app.dealsnest.ui.help;


import com.presentation.app.dealsnest.common.CommonViewInterface;
import com.presentation.app.dealsnest.models.Help;

import java.util.ArrayList;

public interface HelpActivityViewInterface extends CommonViewInterface {

    void onLoadingHelpSuccess(ArrayList<Help> help);

    void onLoadingHelpFailed(String message);
}

