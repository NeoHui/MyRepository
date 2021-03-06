package cn.itcast.controller.company;

import cn.itcast.controller.BaseController;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/list" ,name = "展示企业列表数据")
    public String findAll(){
        List<Company> companyList = companyService.findAll();
        request.setAttribute("list",companyList);
        return "company/company-list";
    }

    @RequestMapping(value = "/toAdd" ,name = "进入新增企业页面")
    public String toAdd( ){
        return "company/company-add";
    }
    @RequestMapping(value = "/edit" ,name = "保存企业数据")
    public String edit(Company company){

//        判断新增还是修改的依据是：company中是否有id值
        if(StringUtils.isEmpty(company.getId())){
//            新增 insert
            company.setId(UUID.randomUUID().toString()); //设置企业id
            companyService.save(company);
        }else{
//            修改 update
            companyService.update(company);
        }
//        重定向到列表页面
        return "redirect:/company/list.do";
    }

    @RequestMapping(value = "/toUpdate" ,name = "进入修改企业页面")
    public String toUpdate(String id){
        Company company = companyService.findById(id);
        request.setAttribute("company",company);
        return "company/company-add";
    }


    @RequestMapping(value = "/delete" ,name = "删除企业数据")
    public String delete(String id){
        companyService.deleteById(id);
//        重定向到列表页面
        return "redirect:/company/list.do";
    }
}
