package airport.server.service.impl;

import airport.server.converter.CityReviewConverter;
import airport.server.dto.CheapestFlightParamsDTO;
import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;
import airport.server.enums.ResponseStatus;
import airport.server.repository.CityReviewRepository;
import airport.server.service.CityReviewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author natalija
 */
@Service
@Slf4j
public class CityReviewServiceImpl implements CityReviewService {

    private CityReviewRepository cityReviewRepository;
    private CityReviewConverter reviewConverter;

    public CityReviewServiceImpl(CityReviewRepository cityReviewRepository, CityReviewConverter reviewConverter) {
        this.cityReviewRepository = cityReviewRepository;
        this.reviewConverter = reviewConverter;
    }

    private ResultDTO handleResultDTO(CityReviewCallBack reviewCallBack) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
            resultDTO.setData(reviewCallBack.getResultsFromDB());
        } catch (Exception e) {
            log.error("Not possible to get data from the database.");
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            resultDTO.setErrorMessage(StringUtils.join("Not possible to get reviews from the database.", e.getMessage()));
        }
        return resultDTO;
    }

    @Override
    public ResultDTO getAllReviews() {
        return handleResultDTO(() -> cityReviewRepository.findAllReviews());
    }

    @Override
    public ResultDTO getAllReviewsLatestResults(int numberOfResults) {
        Pageable pageable = PageRequest.of(0, numberOfResults, Sort.Direction.ASC, "id");
        return handleResultDTO(() -> cityReviewRepository.findAllReviewsPaginated(pageable));
    }

    @Override
    public ResultDTO getAllReviews(String cityName) {
        return handleResultDTO(() -> cityReviewRepository.findAllReviewsByName(cityName));
    }

    @Override
    public ResultDTO getAllReviewsLatestResults(String cityName, int numberOfComments) {
        Pageable pageable = PageRequest.of(0, numberOfComments, Sort.Direction.DESC, "id");
        return handleResultDTO(() -> cityReviewRepository.findAllReviewsByNamePaginated(cityName, pageable));
    }

    private ResultDTO saveUpdateComment(CityReviewDTO reviewDto) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            cityReviewRepository.save(reviewConverter.convertReviewDTOToReview(reviewDto));
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Not possible to apply changes to entity: {}. Error: ", reviewDto, e.getMessage());
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            StringUtils.join("Not possible to save entity: ", reviewDto, ". Error: ", e.getMessage());
        }
        return resultDTO;
    }

    @Override
    public ResultDTO addReview(CityReviewDTO reviewDto) {
        return saveUpdateComment(reviewDto);
    }

    @Override
    public ResultDTO updateReview(CityReviewDTO reviewDto) {
        return saveUpdateComment(reviewDto);
    }

    @Override
    public ResultDTO deleteComment(Long reviewId) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            cityReviewRepository.deleteById(reviewId);
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            resultDTO.setErrorMessage(StringUtils.join("Not possible to delete entity: {}. Error: ", reviewId, e.getMessage()));
            log.error("Not possible to delete entity: {}. Error: ", reviewId, e.getMessage());
        }
        return resultDTO;
    }

    @Override
    public ResultDTO findTheCheapestFlight(CheapestFlightParamsDTO flightParamsDTO) {
        return null;
    }

    //avoid code repetition of the same Result DTO logic
    private interface CityReviewCallBack {
        Object getResultsFromDB();
    }
}
