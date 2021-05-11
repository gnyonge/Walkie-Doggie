<template>
  <div class="" id="mainBox">
    <div>
        <v-icon @click="goback()">mdi-arrow-left</v-icon>
    </div>
    <h5 class="text-center mb-5">반려견 등록페이지</h5>
      <div class="d-flex justify-center">
        <div class="filebox">
          <label for="myfile" class="circle">
            사진</label>
          <input type="file"
              id="photo" name="photo"
              accept="image/*"
              style="display:none;"
            >
        </div> 
      </div>
      <div>
        <v-form>
        <v-flex class="ph-size">
          <v-text-field name="name" label="강아지이름" id="name" v-model="name" type="name" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="age" label="나이(생일)" id="age" v-model="age" type="age" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="weight" label="체중" id="weight" v-model="weight" type="weight" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="note" label="선천적 질병" id="note" v-model="note" type="note" required color="#48B9A8"></v-text-field>
        </v-flex>
        <!-- <v-flex class="ph-size">
          <v-text-field name="note" label="트라우마" id="note" v-model="note" type="note" required color="#48B9A8"></v-text-field>
        </v-flex>
        <v-flex class="ph-size">
          <v-text-field name="note" label="알레르기" id="note" v-model="note" type="note" required color="#48B9A8"></v-text-field>
        </v-flex> -->
      </v-form>
      <div class="d-flex justify-center">
        <router-link to="/calendar"><v-btn id="mainBtn">반려견 등록</v-btn></router-link>
      </div>
      </div>
      
  </div>

</template>

<script>
export default {
  data () {
    return {
      name: '',
      age: '',
      weight: '',
      note: '',
      photo: '',
    }
  },
  methods: {
    goback() {
        this.$router.push('/dogregister')
      }
  }
}
</script>
<style scoped>
.register-btn-pd{
  padding: 30px;
}
.circle {
  width: 150px;
  height: 150px;
  background: #DEB98B;
  border-radius: 50%;
  font-size: 24px;
  text-align:center;
  line-height: 150px;
  /* color: white; */
}
.btn-file{
  position: relative;
  overflow: hidden;
}
.filebox label {
  display: inline-block;
  color: #fff;
  background-color: #F2B75B;
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