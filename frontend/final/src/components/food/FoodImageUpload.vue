<template>
  <div>
    <!-- Styled -->
    <b-form-file
      v-model="file1"
      :state="Boolean(file1)"
      placeholder="사진을 첨부해 주세요!"
      drop-placeholder="이곳에 추가..!"
    ></b-form-file>
  </div>
</template>

<script>
  export default {
    name: "FoodImageUpload",
    data() {
      return {
        file1: null,
      }
    }
  }
</script>

<style>

</style>