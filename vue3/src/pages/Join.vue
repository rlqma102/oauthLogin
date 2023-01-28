<template>
  <div class="joinForm">
    <div class="join">
      <h2>Join</h2>
      <form method="post" action="서버의url" id="join-form">
        <input type="text" name="oaUserName" placeholder="Name" v-model=oaUserName>
        <input type="email" name="oaUserEmail" placeholder="Email" v-model=oaUserEmail>
        <input type="password" name="userPw" placeholder="Password">
        <input type="password" name="userPwCh" placeholder="Password check">
<!--        <label for="remember-check">-->
<!--          <input type="checkbox" id="remember-check">아이디 저장하기-->
<!--        </label>-->

        <input type="submit" value="Join">
      </form>
    </div>
  </div>
</template>

<script setup>
    //let oaUserName = sessionStorage.getItem('name')
    let oaUserName = localStorage.getItem('name')
    let oaUserEmail = localStorage.getItem('email')


    //axios.get('').then((res)=>{
      //console.log(res.data.email);
      // if ( res.status != 200 ) {
      //   window.location.href = '/login';
      // } else {
      //   alert('['+res.data.role+']로그인 되었습니다');
      //   localStorage.setItem('accessToken', res.data.accessToken); // 로컬 스토리지에 저장
      //   //회원테이블에서 이메일 비교 == 없으면 join화면으로 이동
      //
      //   router.push({path:"/join"})
      //   // localStorage.getItem('accessToken'); // 데이터 조회
      //   // window.location.href = '/';
      // }
    //});
  //}

</script>

<style scoped>

*{
  padding: 0;
  margin: 0;
  border: none;
}
body{
  font-size: 14px;
  font-family: 'Roboto', sans-serif;
}

.joinForm{
  width: 400px;
  height: 350px;
  padding: 40px;
  box-sizing: border-box;
  margin: 0 auto;
}

.join-wrapper > h2{
  font-size: 24px;
  color: #6A24FE;
  margin-bottom: 20px;
}
#join-form > input{
  width: 100%;
  height: 48px;
  padding: 0 10px;
  box-sizing: border-box;
  margin-bottom: 16px;
  border-radius: 6px;
  background-color: #F8F8F8;
}
#join-form > input::placeholder{
  color: #D2D2D2;
}
#join-form > input[type="submit"]{
  color: #fff;
  font-size: 16px;
  background-color: #6A24FE;
  margin-top: 20px;
}


#join-form > input[type="checkbox"]{
  display: none;
}
#join-form > label{
  color: #999999;
}
#join-form input[type="checkbox"] + label{
  cursor: pointer;
  padding-left: 26px;
  /*background-image: url("checkbox.png");*/
  background-repeat: no-repeat;
  background-size: contain;
}
#join-form input[type="checkbox"]:checked + label{
  /*background-image: url("checkbox-active.png");*/
  background-repeat: no-repeat;
  background-size: contain;
}
</style>