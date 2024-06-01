package base.base_in_spring.bindingResult;

import base.base_in_spring.domain.user.User;
import base.base_in_spring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/binding-result")
public class BindingResultPracticeController {

    private final UserRepository userRepository;

    @GetMapping
    public String form() {
        return "bindingResult/form";
    }

    /*
    Handler Adapter -> ArgumentResolver 호출 시 구현체인 ModelAttributeMethodProcessor 호출
    내부 resolveArguments:
     */
    @PostMapping("/user") // 자동으로 ModelAttribute 붙음
    public String addUser(User user, BindingResult bindingResult) {
        log.info(">> add-user");

        if (bindingResult.hasErrors()) {
            log.error("오류가 발생했습니다. {}", bindingResult);
            // 에러 처리
            return "redirect:/binding-result";
        }

        // 각 컬럼 존재 여부
        if (StringUtils.hasText(user.getName())) {
//            bindingResult.addError(new FieldError("user", "name", "이름을 입력해주세요."));
            bindingResult.addError(new FieldError("user", "name", user.getName(), false, null, null, "이름을 입력해주세요."));
        }

        // user 이름이 2글자 이상인지 체크
        if (user.getName().trim().length() < 2) {
            bindingResult.addError(new ObjectError("user", "이름을 2글자 이상 입력해주세요."));
        }

        userRepository.save(user);

        return "redirect:/binding-result";
    }
}
