<template>
	<div class="main-content">
		<add-or-update :parent="this" ref="addOrUpdate"></add-or-update>
	</div>
</template>

<script>
import AddOrUpdate from "./add-or-update";
export default {
  data() {
    return {};
  },
  components: {
    AddOrUpdate,
  },
  created() {
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
	.main-content {
		background: #ffffff !important;
		min-height: 100%;
		padding: 20px !important;
		box-shadow: none !important;
		backdrop-filter: none !important;
		border: none !important;
	}
	.main-content::before {
		display: none !important;
	}
</style>
