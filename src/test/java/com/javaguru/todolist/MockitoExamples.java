package com.javaguru.todolist;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MockitoExamples {

    @Mock
    List<String> mockedList;

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected_simple() {
        mockedList.add("dog");
        verify(mockedList).add("dog");
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void whenUseMockAnnotation_thenMockIsInjected() {
        String listData = "one";

        mockedList.add(listData);
        verify(mockedList).add(listData);
        verify(mockedList).add(anyString());
        verify(mockedList).add(any(String.class));
        verify(mockedList).add(ArgumentMatchers.any(String.class));
        assertEquals(0, mockedList.size());

        when(mockedList.size()).thenReturn(100);
        verify(mockedList, times(1)).size();
        verify(mockedList, atLeastOnce()).size();
        verify(mockedList, atMost(2)).size();
        verify(mockedList, atLeast(1)).size();
        verify(mockedList, never()).clear();
        assertEquals(100, mockedList.size());

//        verifyNoMoreInteractions(mockedList);
//        mockedList.isEmpty();
//        verifyNoMoreInteractions(mockedList);

        Map mockMap = mock(Map.class);
        Set mockSet = mock(Set.class);
        verify(mockedList).isEmpty();
        verifyZeroInteractions(mockMap, mockSet);

        mockMap.isEmpty();

        InOrder inOrder = inOrder(mockedList, mockMap);
        inOrder.verify(mockedList).add("one");
        inOrder.verify(mockedList, calls(1)).size();
        inOrder.verify(mockedList).isEmpty();
        inOrder.verify(mockMap).isEmpty();
    }

    //TODO: want to test real object behaviour

    //We can use Mockito Spy to partial mock an object. When we spy on an object, the real methods are being called unless it’s stubbed.

    @Spy
    List<String> spiedList = new ArrayList<>();

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        verify(spiedList).add("one");
        verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    //TODO: want test that arguments passed correctly

    @Captor
    ArgumentCaptor<String> argCaptor;

    @Test
    public void whenUseCaptorAnnotation_thenTheSam() {
        mockedList.add("one");

        verify(mockedList).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    public static class MyDictionary {
        Map<String, String> wordMap;

        MyDictionary(Map<String, String> wordMap) {
            this.wordMap = wordMap;
        }

        public MyDictionary() {
            wordMap = new HashMap<>();
        }

        public void add(final String word, final String meaning) {
            wordMap.put(word, meaning);
        }

        //TODO: want to test
        public String getMeaning(final String word) {
            return wordMap.get(word);
        }
    }

    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    MyDictionary dic = new MyDictionary();

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", dic.getMeaning("aWord"));
    }

    //@InjectMock
    //    Mockito tries to inject mocked dependencies using one of the three approaches, in the specified order.:
    //
    //    Constructor Based Injection – when there is a constructor defined for the class - biggest constructor.
    //    Setter Methods Based – when there are no constructors defined - setter methods.
    //    Field Based – if there are no constructors or field-based injection possible - inject dependencies into the field itself.

    //TODO: inject mock into spy

    @Spy
    MyDictionary spyDic = new MyDictionary();

    @Test
    public void whenUseInjectMocksAnnotationWithSpyF_thenCorrect() {
        when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDic.getMeaning("aWord"));
    }

    MyDictionary spyDicR;

    @Test
    public void whenUseInjectMocksAnnotationWithSpyR_thenCorrect() {
        spyDicR = Mockito.spy(new MyDictionary(wordMap));
        //TODO: test static

        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", spyDicR.getMeaning("aWord"));
    }

    //    ???




























    //What to do with static methods?




















    /*
    Mock static methods - PowerMock, JMockit














    EasyMock implements an interface at runtime,
    Mockito inherits from the target class to create a mocking stub.
    Static methods are associated with a class and cannot be overridden.
    */

}
