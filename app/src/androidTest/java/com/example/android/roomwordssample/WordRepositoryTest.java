package com.example.android.roomwordssample;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4.class)
public class WordRepositoryTest {
    static final Word[] WORD_ENTRIES = {new Word("aaa"), new Word("bbb")};

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private WordDao mWordDao;
    private WordRepository mWordRepository;

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getTargetContext();
        mWordDao = mock(WordDao.class);
        mWordRepository = new WordRepository(mWordDao);
    }

    @Test
    public void insertWord() throws Exception {
        Word word = new Word("word");
        mWordRepository.insert(word);
        Thread.sleep(400);
        verify(mWordDao).insert(word);
    }

    @Test
    public void getAllWords() throws Exception {
        List<Word> words = Arrays.asList(WORD_ENTRIES);
        MutableLiveData<List<Word>> dbData = new MutableLiveData<>();
        dbData.setValue(words);
        when(mWordDao.getAlphabetizedWords()).thenReturn(dbData);
        Observer<List<Word>> observer = mock(Observer.class);
        mWordRepository.getAllWords().observeForever(observer);
        verify(observer).onChanged(words);

    }


}
