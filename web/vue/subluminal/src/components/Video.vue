<template>
    <div class="sub-trailer">
        <h1>Trailer</h1>
        <iframe src="https://www.youtube.com/embed/5XkFaiq0h7Q?rel=0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>
    
    </div>
</template>


<script>
export default {
    name: 'Video'
    
}
</script>

<style scoped>
    .sub-trailer{
        margin: 100px;
    }

    iframe{
        width: 80vw;
        height: 70vh;
        position: relative;
        z-index: 1;
    }
</style>
