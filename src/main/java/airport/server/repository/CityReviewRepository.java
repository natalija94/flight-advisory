package airport.server.repository;

import airport.server.model.CityReview;
import airport.server.model.CityReviewVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author natalija
 */
@Repository
public interface CityReviewRepository extends JpaRepository<CityReview, Long> {
    /**
     * Get all the reviews as view object.
     * @return list of view objects.
     */
    @Query("select new airport.server.model.CityReviewVO(c) from CityReview c")
    List<CityReviewVO> findAllReviews();

    /**
     * Resolves paginates results for reviews for city.
     * @param pageable as pagination
     * @return available cities
     */
    @Query("select new airport.server.model.CityReviewVO(c) from CityReview c")
    List<CityReviewVO> findAllReviewsPaginated(Pageable pageable);

    /**
     * All reviews for city
     * @param name as city
     * @return available cities
     */
    @Query("select new airport.server.model.CityReviewVO(cityReview) from CityReview cityReview " +
            "join City city on cityReview.cityId = city.id " +
            "where city.name = :name")
    List<CityReviewVO> findAllReviewsByName(String name);

    /**
     * Resolves paginated results as reviews for city
     * @param name as city
     * @param pageable as pagination
     * @return available cities
     */
    @Query("select new airport.server.model.CityReviewVO(cityReview) from CityReview cityReview " +
            "join City city on cityReview.cityId = city.id " +
            "where city.name = :name ")
    List<CityReviewVO> findAllReviewsByNamePaginated(String name, Pageable pageable);
}