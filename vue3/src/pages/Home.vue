<template>
  <div class="home">

    <div class="album py-5 bg-light">
      <div class="container">

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
          <!--  state의 item만큼 반복하게 한다-->
          <div class="col" v-for="(item, idx) in state.items" :key="idx">

            <Card :item="item"/>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import {reactive} from "vue";
import Card from "@/components/Card.vue";

export default {
  name: "Home",
  components: {Card},
  setup() {
    var accessToken = localStorage.getItem('accessToken'); // 데이터 조회
    if ( accessToken == undefined || accessToken == null || accessToken == '' ) {
      window.location.href = '/login';
    }

    const state = reactive({
      items:[]
    })

    axios.get("http://localhost:8080/api/items").then((res) => {
      state.items = res.data;
    })

    return {state}
  }
}
</script>

<style scoped>

</style>