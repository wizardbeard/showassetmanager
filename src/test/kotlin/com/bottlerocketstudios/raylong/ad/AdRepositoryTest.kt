package com.bottlerocketstudios.raylong.ad

//import com.bottlerocketstudios.raylong.AbstractSpringIntegrationTest
import com.bottlerocketstudios.raylong.AssetAttributes
import com.bottlerocketstudios.raylong.ShowAssetManager
import org.assertj.core.api.Assertions.assertThat as assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest(classes = [ShowAssetManager::class])
@ComponentScan(basePackages = ["com.bottlerocketstudios.raylong"])
@EnableJpaRepositories(basePackages = ["com.bottlerocketstudios.raylong"])
@EntityScan(basePackages = ["com.bottlerocketstudios.raylong"])
@EnableAutoConfiguration
@RunWith(SpringRunner::class)
open class AdRepositoryTest {

    private val log = LoggerFactory.getLogger(AdRepositoryTest::class.java)

    @Autowired
    private lateinit var adRepository: AdRepository

    @Before
    fun setUp() {
        val container = Ad(
                id = 1L,
                version = 0L,
                name = "test ad container",
                attributes = hashMapOf(AssetAttributes.ID to "1", AssetAttributes.NAME to "test name")//,
        )
        val savedContainer = adRepository.save(container)
        adRepository.flush()
        log.info("Saved container to repo: {}", savedContainer)
    }

    @Test
    fun `Get Ad From Repo and validate attributes`() {
        val container: Ad? = adRepository.getOne(1L)
        assertThat(container).isNotNull
        assertThat(container?.id).isNotNull()
        assertThat(container?.createdDate).isNotNull()
        assertThat(container?.modifiedDate).isNotNull()
        assertThat(container?.attributes).isNotNull
        assertThat(container?.attributes).isNotEmpty
        assertThat(container?.attributes).isEqualTo(hashMapOf(AssetAttributes.ID to "1", AssetAttributes.NAME to "test name"))
//        assertThat(container?.images).isNotNull
//        assertThat(container?.images).isNotEmpty
//        assertThat(container?.ads).isNotNull
//        assertThat(container?.ads).isNotEmpty

        log.info("Retrieved container from repo: {}", container)
    }
}