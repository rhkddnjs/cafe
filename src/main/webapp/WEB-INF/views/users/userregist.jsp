<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>

 <style type="text/css">
        form {
          width: 600px;
          margin: auto;
        }
        form legend {
          font-size: 20px;
          text-align: center;
        }
        form p span {
          font-size: 13px;
          color: rgb(0, 60, 255);
          font-style: italic;
        }
        form .final_btn {
          text-align: center;
        }
      </style>
</head>
<body>
    <form method="post">
    <fieldset>
        <legend>개인회원 회원가입</legend>
        <p>
          <label for="email">이메일</label>
          <input type="email" name="email" id="email">
        <p>
          <label for="nickname">닉네임</label>
          <input type="text" name="nickname" id="nickname">
        </p>
        <div>
         <label for="password">비밀번호</label>
            <input id="password" type="password" name="password"
                   value="${memberVO.password}"/>
                    <br>
          <span class="err_password">* 8~10글자까지만 입력 가능합니다.</span>
        </div>
		<div>
            <label for="confirmPassword">비밀번호 확인</label>
            <input id="confirmPassword" type="password" name="confirmPassword"
                   value="${memberVO.confirmPassword}"/>
		</div>
        <p>
          <input type="checkbox" name="agree" id="agree">
          <label for="agree">모두 동의</label>
          <br>
          <span class="err_agree">* 필수</span>
        </p>
        <p>
            <div>
                <span style="cursor:pointer;" onclick="showHide('id_test_div');">
                    [ 개인정보 수집 및 이용 동의 ]     ▼ 내용보기 
                </span>
            </div>
            <div id="id_test_div" style="display:block;">
                <p>모이락 서비스 이용을 위해 아래와 같이 개인정보를 수집 및 이용합니다.</p> 
                동의를 거부할 권리가 있으며, 동의 거부 시 모이락 회원서비스 이용이 불가합니다.
            </div>
        <p class="final_btn">
          <button type="submit" class="final_btn">작성완료</button>
        </p>
      </fieldset>
    </form> 
</body>
</html>