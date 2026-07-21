package com.strawHat.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strawHat.backend.dto.InterviewResultResponseDto;
import com.strawHat.backend.entity.InterviewQuestion;
import com.strawHat.backend.repository.InterviewQuestionRepository;
import com.strawHat.backend.service.InterviewEvaluationService;
import com.strawHat.backend.service.ai.AIService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Uses the configured AI provider to evaluate a completed interview's
 * answers, producing an overall score plus per-question feedback.
 */
@Service
public class InterviewEvaluationServiceImpl implements InterviewEvaluationService {

    private final AIService aiService;
    private final ObjectMapper objectMapper;
    private final InterviewQuestionRepository interviewQuestionRepository;

    public InterviewEvaluationServiceImpl(
            AIService aiService,
            ObjectMapper objectMapper,
            InterviewQuestionRepository interviewQuestionRepository) {

        this.aiService = aiService;
        this.objectMapper = objectMapper;
        this.interviewQuestionRepository = interviewQuestionRepository;
    }

    /**
     * Builds an evaluation prompt from the given questions/answers, sends it
     * to the AI provider, parses the JSON response, persists per-question
     * scores/feedback, and returns the overall result.
     */
    @Override
    public InterviewResultResponseDto evaluate(List<InterviewQuestion> questions) {

        String prompt = buildEvaluationPrompt(questions);

        String response = aiService.generateContent(prompt)
                .replace("```json", "")
                .replace("```", "")
                .trim();

        try {
            InterviewResultResponseDto result =
                    objectMapper.readValue(response, InterviewResultResponseDto.class);

            applyFeedbackToQuestions(questions, result);

            return result;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to parse AI response.\nAI Response:\n" + response,
                    e
            );
        }
    }

    /** Builds the instruction + question/answer transcript sent to the AI evaluator. */
    private String buildEvaluationPrompt(List<InterviewQuestion> questions) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an expert Java Backend interviewer.

                Evaluate the following interview.

                IMPORTANT:
                - Return ONLY valid JSON.
                - Do NOT use markdown.
                - Do NOT use ```json.
                - Do NOT explain anything.
                - Output must exactly match this structure.

                {
                  "overallRating":"8/10",
                  "technicalScore":80,
                  "communicationScore":75,
                  "strengths":[
                    ""
                  ],
                  "weaknesses":[
                    ""
                  ],
                  "suggestions":[
                    ""
                  ],
                  "questionFeedback":[
                    {
                      "questionOrder":1,
                      "score":8,
                      "feedback":"..."
                    }
                  ]
                }

                Interview:

                """);

        for (InterviewQuestion question : questions) {

            prompt.append("Question ")
                    .append(question.getQuestionOrder())
                    .append(":\n")
                    .append(question.getQuestion())
                    .append("\n");

            prompt.append("Candidate Answer:\n")
                    .append(question.getAnswer())
                    .append("\n\n");
        }

        return prompt.toString();
    }

    /** Copies the AI's per-question score/feedback back onto the question entities and saves them. */
    private void applyFeedbackToQuestions(List<InterviewQuestion> questions,
                                           InterviewResultResponseDto result) {

        if (result.getQuestionFeedback() == null) {
            return;
        }

        result.getQuestionFeedback().forEach(feedback ->
                questions.stream()
                        .filter(question ->
                                question.getQuestionOrder().equals(feedback.getQuestionOrder()))
                        .findFirst()
                        .ifPresent(question -> {
                            question.setScore(feedback.getScore());
                            question.setAiFeedback(feedback.getFeedback());
                        }));

        interviewQuestionRepository.saveAll(questions);
    }
}
