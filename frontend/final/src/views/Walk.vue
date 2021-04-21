<template>
  <div>
    <MainWalk/>
  </div>
</template>

<script>
export default {
  name: 'Walk',
  components: {
    MainWalk: () => import('@/components/Walk/MainWalk'),
  }
}
</script>
