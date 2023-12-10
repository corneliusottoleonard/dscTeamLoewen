from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Iterable as _Iterable, Mapping as _Mapping, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class Coordinate(_message.Message):
    __slots__ = ["x", "y", "value"]
    X_FIELD_NUMBER: _ClassVar[int]
    Y_FIELD_NUMBER: _ClassVar[int]
    VALUE_FIELD_NUMBER: _ClassVar[int]
    x: int
    y: int
    value: float
    def __init__(self, x: _Optional[int] = ..., y: _Optional[int] = ..., value: _Optional[float] = ...) -> None: ...

class Field(_message.Message):
    __slots__ = ["coordinates"]
    COORDINATES_FIELD_NUMBER: _ClassVar[int]
    coordinates: _containers.RepeatedCompositeFieldContainer[Coordinate]
    def __init__(self, coordinates: _Optional[_Iterable[_Union[Coordinate, _Mapping]]] = ...) -> None: ...

class FieldSequence(_message.Message):
    __slots__ = ["fields"]
    FIELDS_FIELD_NUMBER: _ClassVar[int]
    fields: _containers.RepeatedCompositeFieldContainer[Field]
    def __init__(self, fields: _Optional[_Iterable[_Union[Field, _Mapping]]] = ...) -> None: ...

class FitRequest(_message.Message):
    __slots__ = ["fitData"]
    FITDATA_FIELD_NUMBER: _ClassVar[int]
    fitData: FieldSequence
    def __init__(self, fitData: _Optional[_Union[FieldSequence, _Mapping]] = ...) -> None: ...

class FitResponse(_message.Message):
    __slots__ = []
    def __init__(self) -> None: ...

class PredictRequest(_message.Message):
    __slots__ = ["predictData", "steps"]
    PREDICTDATA_FIELD_NUMBER: _ClassVar[int]
    STEPS_FIELD_NUMBER: _ClassVar[int]
    predictData: FieldSequence
    steps: int
    def __init__(self, predictData: _Optional[_Union[FieldSequence, _Mapping]] = ..., steps: _Optional[int] = ...) -> None: ...

class PredictionResponse(_message.Message):
    __slots__ = ["predictedData"]
    PREDICTEDDATA_FIELD_NUMBER: _ClassVar[int]
    predictedData: FieldSequence
    def __init__(self, predictedData: _Optional[_Union[FieldSequence, _Mapping]] = ...) -> None: ...
