<script>
  import TestForm from "./lib/TestForm.svelte";
  import TestGallery from "./lib/TestGallery.svelte";
</script>

<main>
  <div class="container my-5">
    <div class="p-5 text-center bg-body-tertiary rounded-3">
      <h1 class="text-body-emphasis">TL4 Template</h1>
      <p class="lead">Test the store connection by clicking the buttons.</p>
      <TestForm />
      <TestGallery />
    </div>
  </div>
</main>
