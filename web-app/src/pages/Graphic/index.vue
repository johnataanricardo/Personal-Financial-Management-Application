<template>
  <div id="graphic">
    <Menu/>
    <GraphicBar/>
  </div>
</template>

<script>
import Menu from '@/components/Menu'
import FormProfile from '@/components/FormProfile'
import GraphicBar from '@/components/Graphics/GraphicBar'
import axios from 'axios'

const api = process.env.API_URL

export default {
  name: 'Graphic',
  components: { 
    Menu,
    GraphicBar
  },  
  data() {
    return {
    }
  },
  mounted () {
},
  methods: {}
}
</script>

<style scoped>

  @media screen and (max-width: 600px)  {

  }

</style>