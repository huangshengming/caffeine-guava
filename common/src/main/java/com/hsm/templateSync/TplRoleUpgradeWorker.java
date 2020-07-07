package com.hsm.templateSync;

import com.hsm.templateSync.bean.RoleUpgradeBean;
import com.hsm.templatesynchronized.GameTemplateWorker;
import com.hsm.templatesynchronized.IGameTemplateTask;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

public class TplRoleUpgradeWorker extends GameTemplateWorker<Map<Integer, RoleUpgradeBean>> {

    public TplRoleUpgradeWorker(IGameTemplateTask<Map<Integer, RoleUpgradeBean>> task){
        super(task);
    }

    @Override
    public String getFileName() {
        return "tpl_role_upgrade.xml";
    }

    @Override
    protected Map<Integer, RoleUpgradeBean> convertProcess(Element rootElement) {
        Map<Integer, RoleUpgradeBean> map = new HashMap<>();

        for (Object child : rootElement.elements()) {
            Element element = (Element) child;
            RoleUpgradeBean roleUpgradeBean = new RoleUpgradeBean();
            roleUpgradeBean.level = Integer.parseInt(element.elementText("level"));
            roleUpgradeBean.needExp = Long.parseLong(element.elementText("needExp"));
            map.put(roleUpgradeBean.level, roleUpgradeBean);
        }
        return map;
    }
}
