<template>
  <div>
    <!-- Styled -->
    <b-form-file
      v-model="file1"
      :state="Boolean(file1)"
      placeholder="사진을 첨부해 주세요!"
      drop-placeholder="이곳에 추가..!"
    ></b-form-file>
    <!-- <div class="mt-3">Selected file: {{ file1 ? file1.name : '' }}</div> -->

    <!-- Plain mode -->
    <!-- <b-form-file v-model="file2" class="mt-3" plain></b-form-file>
    <div class="mt-3">Selected file: {{ file2 ? file2.name : '' }}</div> -->
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