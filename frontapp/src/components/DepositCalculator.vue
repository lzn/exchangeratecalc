<script setup>
import { ref } from 'vue';

const amount = ref(null);
const rate = ref(null);
const duration = ref(null);
const result = ref('');

const calculateInterest = async () => {
  try {
    const response = await fetch('http://localhost:3000/calculate-interest', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        amount: parseFloat(amount.value),
        rate: parseFloat(rate.value),
        duration: parseInt(duration.value)
      })
    });

    const data = await response.json();

    if (response.ok) {
      result.value = `Future Value: ${data.result.toFixed(2)}`;
    } else {
      result.value = `Error: ${data.error}`;
    }
  } catch (error) {
    result.value = 'An error occurred while calculating.';
    console.error('Fetch error:', error);
  }
};
</script>

<template>
  <main class="container">
    <hgroup>
      <h1>Bank Deposit Interest Calculator</h1>
      <h2>Calculate the future value of your savings.</h2>
    </hgroup>
    <form @submit.prevent="calculateInterest">
      <label for="amount">
        Amount
        <input type="number" id="amount" v-model="amount" required>
      </label>
      <label for="rate">
        Interest Rate (%)
        <input type="number" id="rate" v-model="rate" step="0.01" required>
      </label>
      <label for="duration">
        Duration (months)
        <input type="number" id="duration" v-model="duration" required>
      </label>
      <button type="submit" class="fit-width">Calculate</button>
    </form>
    <div v-if="result">
      <hr>
      <h3>Result:</h3>
      <p>{{ result }}</p>
    </div>
    <article>
      <h3>About Our Calculator</h3>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla.</p>
      <p>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum.</p>
    </article>
  </main>
</template>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 2rem;
}
button.fit-width {
  width: fit-content;
}
</style>
