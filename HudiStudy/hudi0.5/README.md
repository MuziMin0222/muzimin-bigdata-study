数据湖是一个存储企业的各种各样原始数据的大型仓库，其中的数据可供存取，处理，分析以及传输
1. hudi的基本特性/能力  
    * Hudi能够摄入（Ingest）和管理（Manage）基于HDFS之上的大型分析数据集，主要目的是高效的减少入库延时。  
    * Hudi基于Spark来对HDFS上的数据进行更新、插入、删除等。  
    * Hudi在HDFS数据集上提供如下流原语：插入更新（如何改变数据集）；增量拉取（如何获取变更的数据）。  
    * Hudi可以对HDFS上的parquet格式数据进行插入/更新操作。  
    * Hudi通过自定义InputFormat与Hadoop生态系统（Spark、Hive、Parquet）集成。  
    * Hudi通过Savepoint来实现数据恢复。  
    * 目前，Hudi支持Spark 2.x版本，建议使用2.4.4+版本的Spark。
2. Timeline
    * Hudi内部对每个表都维护了一个Timeline，这个Timeline是由一组作用在某个表上的Instant对象组成。Instant表示在某个时间点对表进行操作的，从而达到某一个状态的表示，所以Instant包含Instant Action，Instant Time和Instant State这三个内容，它们的含义如下所示：
    * Instant Action：对Hudi表执行的操作类型，目前包括COMMITS、CLEANS、DELTA_COMMIT、COMPACTION、ROLLBACK、SAVEPOINT这6种操作类型。Instant Time：表示一个时间戳，这个时间戳必须是按照Instant Action开始执行的时间顺序单调递增的。Instant State：表示在指定的时间点（Instant Time）对Hudi表执行操作（Instant Action）后，表所处的状态，目前包括REQUESTED（已调度但未初始化）、INFLIGHT（当前正在执行）、COMPLETED（操作执行完成）这3种状态。
3. 文件及索引
    * Hudi将表组织成HDFS上某个指定目录（basepath）下的目录结构，表被分成多个分区，分区是以目录的形式存在，每个目录下面会存在属于该分区的多个文件，类似Hive表，每个Hudi表分区通过一个分区路径（partitionpath）来唯一标识
    * 在每个分区下面，通过文件分组（File Group）的方式来组织，每个分组对应一个唯一的文件ID。每个文件分组中包含多个文件分片（File Slice），每个文件分片包含一个Base文件（*.parquet），这个文件是在执行COMMIT/COMPACTION操作的时候生成的，同时还生成了几个日志文件（*.log.*），日志文件中包含了从该Base文件生成以后执行的插入/更新操作。Hudi采用MVCC设计，当执行COMPACTION操作时，会合并日志文件和Base文件，生成新的文件分片。CLEANS操作会清理掉不用的/旧的文件分片，释放存储空间
    * Hudi会通过记录Key与分区Path组成Hoodie Key，即Record Key+Partition Path，通过将Hoodie Key映射到前面提到的文件ID，具体其实是映射到file_group/file_id，这就是Hudi的索引。一旦记录的第一个版本被写入文件中，对应的Hoodie Key就不会再改变了
4. hudi表的类型
    * Copy-on-write表（读多写少的情况）  
    使用专门的列式文件格式存储数据，例如Parquet格式。更新时保存多版本，并且在写的过程中通过异步的Merge来实现重写（Rewrite）数据文件。Copy-On-Write表只包含列式格式的Base文件，每次执行COMMIT操作会生成新版本的Base文件，最终执行COMPACTION操作时还是会生成列式格式的Base文件。所以，Copy-On-Write表存在写放大的问题，因为每次有更新操作都会重写（Rewrite）整个Base文件
    * merge-on-read表（写多读少的情况）  
    使用列式和行式文件格式混合的方式来存储数据，列式文件格式比如Parquet，行式文件格式比如Avro。更新时写入到增量（Delta）文件中，之后通过同步或异步的COMPACTION操作，生成新版本的列式格式文件。Merge-On-Read表存在列式格式的Base文件，也存在行式格式的增量（Delta）文件，新到达的更新都会写到增量日志文件中，根据实际情况进行COMPACTION操作来将增量文件合并到Base文件上。通常，需要有效的控制增量日志文件的大小，来平衡读放大和写放大的影响。Merge-On-Read表可以支持Snapshot Query和Read Optimized Query
5. hudi表的查询类型
    * Snapshot query  
    只能查询到给定COMMIT或COMPACTION后的最新快照数据。  
    对于Copy-On-Write表，Snapshot Query能够查询到，已经存在的列式格式文件（Parquet文件）；  
    对于Merge-On-Read表，Snapshot Query能够查询到，通过合并已存在的Base文件和增量日志文件得到的数据
    * Incremental Query  
    只能查询到最新写入Hudi表的数据，也就是给定的COMMIT/COMPACTION之后的最新数据。
    * Read Optimized Query  
    只能查询到给定的COMMIT/COMPACTION之前所限定范围的最新数据。也就是说，只能看到列式格式Base文件中的最新数据
6. hudi有关依赖
```xml
        <dependency>
            <groupId>org.apache.hudi</groupId>
            <artifactId>hudi-client</artifactId>
            <version>0.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hudi</groupId>
            <artifactId>hudi-hive</artifactId>
            <version>0.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hudi</groupId>
            <artifactId>hudi-spark-bundle_2.11</artifactId>
            <version>0.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hudi</groupId>
            <artifactId>hudi-common</artifactId>
            <version>0.5.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.hudi</groupId>
            <artifactId>hudi-hadoop-mr-bundle</artifactId>
            <version>0.5.3</version>
        </dependency>
```
