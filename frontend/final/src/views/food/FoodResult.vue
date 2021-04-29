<template>
  <div>
    <div id="date"><h2 class="my-10">성분 분석 결과</h2></div>
    <div class="mt-15 d-flex justify-center">
      <div id="tempimg">사진 업로드 하면 여기 띄워짐!</div>
    </div>
    <div id="date" class="mt-5"><h4 class="mt-2">조금은 주의가 필요한 성분들이에요.</h4></div>
    <v-card
    class="mx-auto my-5"
    max-width="344"
    outlined
    v-for="i, idx in 3"
    :key="idx"
  >
    <v-list-item three-line>
      <v-list-item-avatar
        tile
        size="80"
        color="grey"
      ></v-list-item-avatar>
      <v-list-item-content>
        <!-- <div class="overline mb-4">
          성분 이름
        </div> -->
        <v-list-item-title class="headline mb-1">
          성분 이름
        </v-list-item-title>
        <v-list-item-subtitle>Greyhound divisely hello coldly fonwderfully</v-list-item-subtitle>
      </v-list-item-content>

    </v-list-item>
  </v-card>
  </div>
</template>

<script>
export default {
  name: "FoodResult",
}
</script>

<style>

</style>