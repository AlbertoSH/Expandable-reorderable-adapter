# Expandable Reorderable Adapter

[ ![Download](https://api.bintray.com/packages/albertosh/maven/expandable-reorderable-adapter/images/download.svg) ](https://bintray.com/albertosh/maven/expandable-reorderable-adapter/_latestVersion)

## Overview

**ExpandableReorderableAdapter** allows you to implement easily the equivalent of `ExpandableListView` with a `RecyclerView`. It also has the reorder logic implemented.


## Usage
* Make your parent item model class subclass `ParentItem`. In case your class has already a super class make it implements `IParentItem` and override its methods as ParentItem does
* Make your child item model implement `IChildItem`
* Make your `RecyclerView.ViewHolder` classes extend `ChildViewHolder<YourChildClass>` and `ParentViewHolder<YourParentClass>`
* Make your `RecyclerView.Adapter` extends `ExpandableReorderableAdapter<YourParentViewHolder, YourChildViewHolder>`
* If you want to add reorderable behaviour use the `YourAdapter(modelList, recyclerView)` constructor. For dynamic enable/disable behaviour use `Reorderable` static methods

Check the sample for a more detailed view.

##License
 
    Copyright 2016 Alberto Sanz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.