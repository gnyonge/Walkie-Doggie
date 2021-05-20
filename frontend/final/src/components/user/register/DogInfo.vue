<template>
  <div class="register-btn-pd" id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goto()">mdi-arrow-left</v-icon>
      </div>
      <div>
        반려견 등록
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div class="d-flex justify-center">
      <div class="circle " @click="gotoregi()">
        <p>+ 반려견 등록</p>
      </div>
    </div>
    <div class="d-flex justify-center mt-15">
      <div>
        <p>※ 다견가정을 위해</p>
        <p>최대 세마리까지 등록 가능합니다.</p>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
export default {
  name: "DogInfo",
  computed: {
    ...mapGetters(['getDogInfo', 'getUser'])
  },
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
      }
  },
  methods: {
    goto() {
      if (!this.getDogInfo) {
        this.$router.push('/register')
      } else {
        this.$router.push('/mypage')
      }
      
    },
    gotoregi() {
      this.$router.push('/dogregisternext')
    }
  }
}
</script>
<style scoped>
.register-btn-pd{
  padding: 30px;
}
.circle {
  width: 200px;
  height: 200px;
  background: #F2B75B;
  border-radius: 50%;
  font-size: 28px;
  text-align:center;
  line-height: 200px;
  color: white;
  margin-top: 30%;
}
.filebox label {
  display: inline-block;
  color: #fff;
  background-color: #DEB98B;
  cursor: pointer;
  -webkit-transition: background-color 0.2s;
  transition: background-color 0.2s;
  width: 150px;
  height: 150px;
  border-radius: 50%;
  font-size: 24px;
  text-align:center;
  line-height: 150px;
}

.filebox label:hover {
  background-color: #cc9653;
}

.filebox label:active {
  background-color: #754812;
}

.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  border: 0;
}
</style>