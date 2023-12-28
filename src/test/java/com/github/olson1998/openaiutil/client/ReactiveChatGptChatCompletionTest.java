package com.github.olson1998.openaiutil.client;

import com.github.olson1998.http.contract.WebRequest;
import com.github.olson1998.openaiutil.model.TestUtil;
import com.github.olson1998.openaiutil.model.ex.ChatCompletion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.github.olson1998.http.HttpMethod.POST;
import static com.github.olson1998.openaiutil.model.ex.ChatCompletionTestModel.CHAT_COMPLETION;
import static com.github.olson1998.openaiutil.model.req.ChatRequestTestModel.CHAT_REQUEST;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class ReactiveChatGptChatCompletionTest extends ChatGptReactiveClientTest{

    @Captor
    private ArgumentCaptor<WebRequest> webRequestArgumentCaptor;

    @Test
    void shouldSendRequestForChatCompletion(){
        given(reactiveRestClient.sendHttpRequest(any(WebRequest.class), eq(ChatCompletion.class)))
                .willReturn(Mono.just(TestUtil.webResponseOk(CHAT_COMPLETION)));

        var chatCompletionResponse = chatGptReactiveClient().postChatRequest(CHAT_REQUEST)
                .block();

        then(reactiveRestClient).should().sendHttpRequest(webRequestArgumentCaptor.capture(), eq(ChatCompletion.class));

        assertThat(chatCompletionResponse).isNotNull();
        var chatCompletion = chatCompletionResponse.body();
        assertThat(chatCompletion).isNotNull().isEqualTo(CHAT_COMPLETION);
        var webRequest = webRequestArgumentCaptor.getValue();
        assertThat(webRequest.httpMethod()).isEqualTo(POST);
        var chatReq = webRequest.body();
        assertThat(chatReq).isNotNull().isEqualTo(CHAT_REQUEST);
        assertThat(webRequest.httpHeaders())
                .isNotNull()
                .containsEntry(ACCEPT, List.of(APPLICATION_JSON.getMimeType()));
    }

}
