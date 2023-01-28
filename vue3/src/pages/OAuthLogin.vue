

<script>
  import axios from "axios";
  import router from "@/scripts/router";

  var param = new URLSearchParams(window.location.search);
  let code = param.get("code");

  export default {
    setup() {
      if ( code == null || code == undefined || code == '' ) {
        window.location.href = '/login';
      }

      axios.get('http://localhost:8080/redirect/oauth/google?code='+code).then((res)=>{
        console.log(res);
        if ( res.status != 200 ) {
          window.location.href = '/login';
        } else {
          alert('['+res.data.role+']로그인 되었습니다');
          localStorage.setItem('accessToken', res.data.accessToken);
          localStorage.setItem('name', res.data.name);
          localStorage.setItem('email', res.data.email);// 로컬 스토리지에 저장
          //회원테이블에서 이메일 비교 == 없으면 join화면으로 이동

          router.push({path:"/join"})
          // localStorage.getItem('accessToken'); // 데이터 조회
          // window.location.href = '/';
        }
      });
    }
  }
</script>

<style scoped>
</style>