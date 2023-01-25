<template>
  <div class="loginForm">

    <div class="login-wrapper">
      <h2>Login</h2>
      <form method="post" action="서버의url" id="login-form">
        <input type="text" name="userName" placeholder="Email">
        <input type="password" name="userPassword" placeholder="Password">
        <label for="remember-check">
          <input type="checkbox" id="remember-check">아이디 저장하기
        </label>
        <input type="submit" value="Login">
        <input class="googleButton" type="button" @click="googleLogin" value="GOOGLE LOGIN">
      </form>
    </div>



  </div>

</template>

<script>
  import {reactive} from "vue";
  import axios from "axios";
  import store from "@/scripts/store";
  import router from "@/scripts/router";

  export default {
    setup() {
      // 로그인 페이지 고려해야하는 것
      // 1. 이미 로그인한 사용자 일 경우 home 으로 보낸다
      // 2. 이미 로그인한 사용자 일 경우 기존 로그인 정보를 삭제하고 다시 로그인하게 한다
      let accessToken = localStorage.getItem('accessToken'); // 데이터 조회
      if ( accessToken != undefined && accessToken != null && accessToken != '' ) {
        alert("이미 로그인한 사용자 입니다");
      }

      const state = reactive({
        form :{
          email: "",
          password: ""
        }
      })
      const submit = ()=> {
        axios.post("/api/account/login", state.form).then((res)=> {
          store.commit('setAccount', res.data)
          console.log(res.data);
          //sessionstorage에 로그인 정보 보관
          sessionStorage.setItem("id", res.data)
          router.push({path:"/"})
          window.alert("로그인하였습니다");
        }).catch(()=> {
          window.alert("로그인 정보가 존재하지 않습니다.");
        })
      }
      return {state, submit}
    },
    methods: {
      googleLogin() {
        window.location = 'https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email&client_id=130018349769-enrk74a1tvt8rhaoockie6gv2gd8ntm0.apps.googleusercontent.com&response_type=code&redirect_uri=http://localhost:3000/redirect/oauth&access_type=offline';
      }
    }
  }
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
.loginForm{

}

.login-wrapper{
  width: 400px;
  height: 350px;
  padding: 40px;
  box-sizing: border-box;
  margin: 0 auto;
}

.login-wrapper > h2{
  font-size: 24px;
  color: #6A24FE;
  margin-bottom: 20px;
}
#login-form > input{
  width: 100%;
  height: 48px;
  padding: 0 10px;
  box-sizing: border-box;
  margin-bottom: 16px;
  border-radius: 6px;
  background-color: #F8F8F8;
}
#login-form > input::placeholder{
  color: #D2D2D2;
}
#login-form > input[type="submit"]{
  color: #fff;
  font-size: 16px;
  background-color: #6A24FE;
  margin-top: 20px;
}

.googleButton {
  color: #6A24FE;
  border: 1px solid #6A24FE;
}

#login-form > input[type="checkbox"]{
  display: none;
}
#login-form > label{
  color: #999999;
}
#login-form input[type="checkbox"] + label{
  cursor: pointer;
  padding-left: 26px;
  /*background-image: url("checkbox.png");*/
  background-repeat: no-repeat;
  background-size: contain;
}
#login-form input[type="checkbox"]:checked + label{
  /*background-image: url("checkbox-active.png");*/
  background-repeat: no-repeat;
  background-size: contain;
}
</style>