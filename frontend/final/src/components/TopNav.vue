<template>
  <div>
    <div>
    <v-img id="header" src="@/assets/header.png" @click="goto()"></v-img>
    </div>
  </div>
</template>

<script>
  export default {
    name: "TopNav",
    methods: {
      goto() {
        this.$router.push('/calendar')
      }
    }
  }
</script>
<style>
#header {
  width: 200px;
  height: 100%;
}
</style>