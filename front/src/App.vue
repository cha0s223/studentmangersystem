<template>

<n-dialog-provider>
<n-message-provider>
<router-view>
</router-view>
</n-message-provider>
</n-dialog-provider>
</template>

<script setup>

</script>

<style>
* {
  margin: 0;
  padding: 0;
  font-family: "Nunito";
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}
html {
  height: 100%;
}

#app {
  max-height: 100%;
  min-width: 100%;
  position: relative;
}
html,body,#app{
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}
</style>