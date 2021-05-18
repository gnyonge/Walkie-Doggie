<template>
  <div>
    <MainWalk/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'

export default {
  name: 'Walk',
  components: {
    MainWalk: () => import('../../components/walk/MainWalk'),
  },
  mounted(){
    // 뒤로 가기 눌렀을 때 저장되어 있던 정보도 지우기 
    this.deletePostingContent()
    this.deletePostingWid()
    this.deleteMyPath()
    this.setNowLon(Number)
    this.setNowLat(Number)
    this.setFirstAreaName('')
    this.setTempPhotoURL('')
    this.setAreaName('')
    this.setLikeCnt(0)
    this.setSelectedItem(null)
  },
  methods:{
    ...mapMutations([
      'setWid', 
      'deletePostingWid', 
      'deletePostingContent',
      'deleteMyPath',
      'setNowLon',
      'setNowLat',
      'setFirstAreaName',
      'setTempPhotoURL',
      'setAreaName',
      'setLikeCnt',
      'setSelectedItem']),
  }
}
</script>
