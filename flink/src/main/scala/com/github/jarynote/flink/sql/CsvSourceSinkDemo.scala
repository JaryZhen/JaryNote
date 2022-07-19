/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.study.sql

import java.io.{File, FileOutputStream, OutputStreamWriter}

import org.apache.flink.api.common.typeinfo.{TypeInformation, Types}
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.types.Row

/**
  * 期望的结果:
  *
  * Mike,1,12.3,Smith
  * Bob,2,45.6,Taylor
  * Sam,3,7.89,Miller
  * Peter,4,0.12,Smith
  * Liz,5,34.5,Williams
  * Sally,6,6.78,Miller
  * Alice,7,90.1,Smith
  * Kelly,8,2.34,Williams
  */
object CsvSourceSinkDemo {
  def main(args: Array[String]): Unit = {

  }

}
