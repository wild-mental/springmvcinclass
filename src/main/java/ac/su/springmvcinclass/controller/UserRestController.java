package ac.su.springmvcinclass.controller;

import ac.su.springmvcinclass.domain.UserBmiDTO;
import ac.su.springmvcinclass.domain.UserDTO;
import ac.su.springmvcinclass.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController  // View 엔진 없이 콘텐츠를 직접 응답
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();  // 1) 전체 리스트 User Entity 를 DTO 로 수신 필요
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserDTOByID(id);  // 2) 유저 개별 데이터를 DTO 로 수신 필요
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        UserDTO savedUser = userService.createUser(user);  // 3) 유저 생성 데이터도 DTO 로 Service 에 전달
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);  // 4) 유저 업데이트도 DTO 로 전달
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Long id, @RequestBody UserDTO user) {
        // 변경 하고자 하는 필드명 targetFields = [ fieldA, fieldB, ... ]
        // 규약에 명시된 제약 조건 외에는 자유도 있게 input - output 설계 가능!
        UserDTO patchedUser = userService.patchUser(id, user);  // 5) 유저 업데이트도 DTO 로 전달
        return ResponseEntity.ok(patchedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/response-entity-sample")
    public ResponseEntity<Map<String, String>> getCustomResponse() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "add body msg test");

        HttpHeaders headers = new HttpHeaders();  // HttpHeaders : Spring Framework 지원 클래스 사용
        headers.add("Custom-Header", "add header content test");
        // 1) 응답 헤더는 현재 조회한 페이지를 기준으로 추가 Navigating 을 가이드하는 용도
        //    ex - 추가로 통신에 활용되는 데이터 상태 (본 페이지가 보안적으로 현재 요청에 의해 표시되기에 안전한지의 여부)
        //         CORS, Cross Origin Resource Sharing 허용 여부 표시
        //         사이트 간에 데이터=Resource 를 부분적으로 완성해가면서 Client에서 원하는 최종 콘텐츠가 완성되는 경우
        //         -> 교차 출처 리소스 공유 어뷰징을 통해서 "특정 페이지의 콘텐츠를 날조, 사기적으로 조합" 시킬 수 있음
        //         => 고객 행위를 교란시킬 수 있음
        //         : Cross-Origin-Resource-Policy 헤더 및 부속 헤더 몇가지를 사용해서 차단 가능
        //         Header 는 텍스트일 뿐인데, 보안 정책을 준수할 수 있게 강제하는 역할은 Browser 가 수행함
        // 2) 현재 조회 페이지 자체에 대해서 Data 가 아닌, MetaData 를 응답하는 위치로 사용
        //    ex - 페이지에서 응답하는 데이터 총량을 표시, 로그인 유저의 현재 권한 상태
        //    => Header 만 미리 받아보고 후속동작을 수행하는 클라이언트 동작이 가능해짐

        return ResponseEntity.status(HttpStatus.ACCEPTED)
            .headers(headers)
            .body(responseBody);
    }

    @GetMapping("/bmi")
    public ResponseEntity<List<UserBmiDTO>> getAllUsersWithBmi() {
        List<UserBmiDTO> users = userService.findAllUserWithBmi();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/bmi")
    public ResponseEntity<UserBmiDTO> getUserWithBmi(@PathVariable Long id) {
        UserBmiDTO user = userService.findUserBmiDTOById(id);
        return ResponseEntity.ok(user);
    }
}
