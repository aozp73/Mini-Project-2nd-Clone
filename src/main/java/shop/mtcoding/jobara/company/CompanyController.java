package shop.mtcoding.jobara.company;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.jobara.common.aop.CompanyCheck;
import shop.mtcoding.jobara.common.config.auth.LoginUser;
import shop.mtcoding.jobara.common.dto.ResponseDto;
import shop.mtcoding.jobara.company.dto.CompanyReq.CompanyJoinReqDto;
import shop.mtcoding.jobara.company.dto.CompanyReq.CompanyUpdateReqDto;
import shop.mtcoding.jobara.company.dto.CompanyResp.CompanyInfo;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private HttpSession session;

    @GetMapping("/company/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id) {
        CompanyInfo companyInfo = companyService.getCompany(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "기업 회원 정보 불러오기 성공", companyInfo), HttpStatus.OK);
    }

    @PostMapping("/joinCompany")
    public ResponseEntity<?> join(@RequestBody @Valid CompanyJoinReqDto companyJoinReqDto, 
            BindingResult bindingResult) {
        companyService.insertCompany(companyJoinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "기업 회원 가입 성공", null), HttpStatus.CREATED);
    }

    @PutMapping("/company/{id}")
    @CompanyCheck
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Valid CompanyUpdateReqDto companyUpdateReqDto, 
            BindingResult bindingResult) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        companyService.updateCompany(companyUpdateReqDto, loginUser.getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "기업 회원 수정 성공", null), HttpStatus.CREATED);
    }
}
