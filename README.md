# TextAnalysisz
中文语义分析（用两种方法----中文极性词典NTUSD 和 机器学习）：
基于平台（java + jieba分词 + word2Vec + libsvm ）
1.基于中文极性词典(NTUSD)：
  sentence 通过结巴分词然后和中文极性词库进行对比，判断这段话的情感性别。

2.基于机器学习的语敢分析：
  sentence 通过结巴分词，然后word2vec转换成向量，然后训练libsvm, 对测试语句同样转换成向量，利用libsvm进行预测。

