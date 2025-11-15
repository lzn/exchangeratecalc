<template>
  <nav>
    <ul>
      <li><router-link to="/">Kalkulator lokat</router-link></li>
      <li><router-link to="/credit">Kalkulator kredytu</router-link></li>
    </ul>
  </nav>
</template>

<script setup>
</script>

<style scoped>
nav {
  background-color: #f8f9fa;
  padding: 1rem;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
}
li {
  margin: 0 1rem;
}
</style>
