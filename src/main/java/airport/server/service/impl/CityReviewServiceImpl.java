package airport.server.service.impl;

import airport.server.converter.CityReviewConverter;
import airport.server.dto.CheapestFlightParamsDTO;
import airport.server.dto.CityReviewDTO;
import airport.server.dto.ResultDTO;
import airport.server.enums.ResponseStatus;
import airport.server.repository.CityCommentRepository;
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

    private CityCommentRepository cityCommentRepository;
    private CityReviewConverter reviewConverter;

    public CityReviewServiceImpl(CityCommentRepository cityCommentRepository, CityReviewConverter reviewConverter) {
        this.cityCommentRepository = cityCommentRepository;
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
    public ResultDTO getAllComments() {
        return handleResultDTO(() -> cityCommentRepository.findAllReviews());
    }

    @Override
    public ResultDTO getAllCommentsLatestResults(int numberOfResults) {
        Pageable pageable = PageRequest.of(0, numberOfResults, Sort.Direction.ASC, "id");
        return handleResultDTO(() -> cityCommentRepository.findAllReviewsPaginated(pageable));
    }

    @Override
    public ResultDTO getAllComments(String cityName) {
        return handleResultDTO(() -> cityCommentRepository.findAllReviewsByName(cityName));
    }

    @Override
    public ResultDTO getAllCommentsLatestResults(String cityName, int numberOfComments) {
        Pageable pageable = PageRequest.of(0, numberOfComments, Sort.Direction.ASC, "id");
        return handleResultDTO(() -> cityCommentRepository.findAllReviewsByNamePaginated(cityName, pageable));
    }

    private ResultDTO saveUpdateComment(CityReviewDTO reviewDto) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            cityCommentRepository.save(reviewConverter.convertReviewDTOToReview(reviewDto));
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Not possible to apply changes to entity: {}. Error: ", reviewDto, e.getMessage());
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            StringUtils.join("Not possible to save entity: ", reviewDto, ". Error: ", e.getMessage());
        }
        return resultDTO;
    }

    @Override
    public ResultDTO addComment(CityReviewDTO reviewDto) {
        return saveUpdateComment(reviewDto);
    }

    @Override
    public ResultDTO updateComment(CityReviewDTO reviewDto) {
        return saveUpdateComment(reviewDto);
    }

    @Override
    public ResultDTO deleteComment(CityReviewDTO reviewDto) {
        ResultDTO resultDTO = new ResultDTO();
        try {
            cityCommentRepository.delete(reviewConverter.convertReviewDTOToReview(reviewDto));
            resultDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            resultDTO.setResponseStatus(ResponseStatus.ERROR);
            resultDTO.setErrorMessage(StringUtils.join("Not possible to delete entity: {}. Error: ", reviewDto, e.getMessage()));
            log.error("Not possible to delete entity: {}. Error: ", reviewDto, e.getMessage());
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
