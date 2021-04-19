import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState()
  ],
  state: {
    nowTab: 0
  },
  getters: {
    getNowTab(state) {
      return state.nowTab
    }
  },
  mutations: {
    setNowTab(state, now) {
      state.nowTab = now
    }
  },
  actions: {
  },
})
