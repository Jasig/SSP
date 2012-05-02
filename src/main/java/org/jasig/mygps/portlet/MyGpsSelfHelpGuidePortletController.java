package org.studentsuccessplan.mygps.portlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class MyGpsSelfHelpGuidePortletController extends AbstractMyGpsController {

    @RenderMapping
    public String show() {
        return "self-help-guide";
    }
    
}
