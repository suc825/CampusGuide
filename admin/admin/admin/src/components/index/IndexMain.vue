<template>
  <div class="admin-shell">
    <div class="admin-shell__glow admin-shell__glow--left"></div>
    <div class="admin-shell__glow admin-shell__glow--right"></div>
    <aside class="admin-shell__aside">
      <index-aside></index-aside>
    </aside>
    <div class="admin-shell__page">
      <index-header class="admin-shell__header"></index-header>
      <main class="admin-shell__main">
        <bread-crumbs :title="title" class="admin-shell__breadcrumbs"></bread-crumbs>
        <section class="admin-shell__content">
          <router-view></router-view>
        </section>
      </main>
    </div>
  </div>
</template>

<script>
import IndexAside from '@/components/index/IndexAsideStatic'
import IndexHeader from '@/components/index/IndexHeader'

export default {
  components: {
    IndexAside,
    IndexHeader
  },
  data() {
    return {
      title: ''
    };
  }
};
</script>

<style lang="scss" scoped>
.admin-shell {
  position: relative;
  min-height: 100vh;
  padding: 20px;
}

.admin-shell__glow {
  position: fixed;
  z-index: 0;
  border-radius: 999px;
  pointer-events: none;
  filter: blur(12px);
}

.admin-shell__glow--left {
  top: 60px;
  left: -90px;
  width: 240px;
  height: 240px;
  background: rgba(47, 122, 105, 0.14);
}

.admin-shell__glow--right {
  top: 120px;
  right: 40px;
  width: 180px;
  height: 180px;
  background: rgba(212, 170, 99, 0.18);
}

.admin-shell__aside {
  position: fixed;
  top: 20px;
  left: 20px;
  bottom: 20px;
  z-index: 12;
  width: 280px;
}

.admin-shell__page {
  position: relative;
  z-index: 1;
  min-height: calc(100vh - 40px);
  margin-left: 300px;
}

.admin-shell__header {
  position: sticky;
  top: 20px;
  z-index: 10;
}

.admin-shell__main {
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding-top: 18px;
}

.admin-shell__breadcrumbs {
  min-height: 58px;
}

.admin-shell__content {
  min-height: calc(100vh - 150px);
}

@media (max-width: 1080px) {
  .admin-shell {
    padding: 16px;
  }

  .admin-shell__aside {
    position: relative;
    top: auto;
    left: auto;
    bottom: auto;
    width: 100%;
    margin-bottom: 16px;
  }

  .admin-shell__page {
    margin-left: 0;
    min-height: auto;
  }

  .admin-shell__header {
    position: relative;
    top: auto;
  }

  .admin-shell__content {
    min-height: auto;
  }
}
</style>
